package me.khrystal.threesome.util;


import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * usage: RxBus use RxJava
 *        TODO this RxBus will backpressure when post lost message at the same time need fix
 * author: kHRYSTAL
 * create time: 17/12/13
 * update time:
 * email: 723526676@qq.com
 */

public class RxBus {

    private final Subject<Object, Object> bus;

    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getDefault() {
        return Holder.INSTANCE;
    }

    public void post(Object o) {bus.onNext(o);}

    public <T>Observable<T> toObservable(final Class<T> eventType) {
        return bus.filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object o) {
                return eventType.isInstance(o);
            }
        }).cast(eventType);
    }

    private static final class Holder {
        private static RxBus INSTANCE = new RxBus();
    }


}
