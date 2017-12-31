from django.shortcuts import render


# Create your views here.
def index(request):
    page_index = int(request.GET.get('index', 0))
    page_index += 1
    return render(request, 'index.html', {'index': str(page_index)})
