/**
 * Created by kHRYSTAL on 17/12/15.
 */
const fs = require('fs');
const path = require('path');
const gulp = require('gulp');
const rollup = require('rollup').rollup;
const babel = require('rollup-plugin-babel');
const json = require('rollup-plugin-json');
const uglify = require('uglify-js');

const {version} = require('../package.json');

const banner =
    '/**!\n' +
    ' * threesome-sdk.js v' + version + '\n' +
    ' * (c) 2017-' + new Date().getFullYear() + '\n' +
    ' * author kHRYSTAL\n' +
    ' */';


if (!fs.existsSync('../dist')) {
    fs.mkdirSync('../dist')
}

gulp.task("build", function () {
    return rollup({
        entry: path.resolve(__dirname, '../src/entry.js'),
        plugins: [
            json(),
            babel()
        ]
    })
        .then((bundle) => {
            bundle.write({
                dest: '../dist/threesome-sdk-' + version + '.build.js',
                sourceMap: true,
                format: 'umd',
                moduleName: 'threesome'
            });
            return bundle
        })
        .then((bundle) => {
            var code = bundle.generate({
                format: 'umd',
                moduleName: 'threesome'
            }).code;
            return read(path.resolve(__dirname, '../src/lib/promise.js'), code);
        })
        .then(({data, bundle}) => {
            return data + bundle;
        })
        .then((code) => {
            return write('../dist/threesome-sdk-' + version + '.js', banner ? (banner + '\n' + code) : code, code);
        })
        .then((code) => {
            var minified = uglify.minify(code, {
                    fromString: true,
                    output: {
                        screw_ie8: false,
                        ascii_only: true
                    },
                    compress: {
                        pure_funcs: ['makeMap']
                    }
                }).code;
            return write(path.resolve(__dirname, '../dist/threesome-sdk-' + version + '.min.js'), minified)
        })
        .catch((err) => {
            console.log(err);
        })
});

function write(dest, code, bundle) {
    return new Promise(function (resolve, reject) {
        fs.writeFile(dest, code, function (err) {
            if (err) return reject(err);
            console.log(blue(path.relative(process.cwd(), dest)) + ' ' + getSize(code))
            resolve(bundle)
        });
    })
}

function read(src, bundle) {
    return new Promise(function (resolve, reject) {
        fs.readFile(src, 'utf8', function (err, data) {
            blue(err);
            if (err) return reject(err);
            resolve(bundle ? {data, bundle} : data)
        });
    })
}


function getSize(code) {
    return (code.length / 1024).toFixed(2) + 'kb'
}


function blue(str) {
    return '\x1b[1m\x1b[34m' + str + '\x1b[39m\x1b[22m'
}
