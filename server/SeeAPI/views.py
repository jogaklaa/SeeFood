# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render, render_to_response, get_object_or_404, redirect
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse
from .models import *
from PIL import Image
from django.conf import settings
from django.core.files.storage import FileSystemStorage



def homepageView(request):
    if request.method == 'POST' and request.FILES['myfile']:
        print( request.FILES)
        myfile = request.FILES['myfile']
        fs = FileSystemStorage()
        # filename = fs.save(myfile.name, myfile)
        # uploaded_file_url = fs.url(filename)
        return JsonResponse({'didItWork':"it worked!"})
    return render(request, 'index.html')

def photoCheck(request):
    fs = FileSystemStorage()
    values = {}
    for newImage in request.FILES:
        newImageObject = image.objects.create(photo = request.FILES[newImage])
        im = Image.open(request.FILES[newImage])
        # TODO: get actual file size
        size = [200,200]
        im_resized = im.resize(size, Image.ANTIALIAS)
        filename = "photos/"+request.FILES[newImage].name[:request.FILES[newImage].name.index('.')-1]+'resized.png'
        im_resized.save(filename)
        #need to check validity here
        #need to send to api here
        positive = 1.5
        negative = 1.1
        newImageObject.positiveCertainty = positive
        newImageObject.negativeCertainty = negative
        newImageObject.save()
        print({"positive":newImageObject.positiveCertainty, "negative":newImageObject.negativeCertainty})
        values[filename] = {"positive":newImageObject.positiveCertainty, "negative":newImageObject.negativeCertainty}
    print(values)
    return JsonResponse(values)

def photoQeury(request):
    # to get the last 10 things
    # last_ten = Messages.objects.filter(since=since).order_by('-id')[:10]
    # last_ten_in_ascending_order = reversed(last_ten)


    # use something like this to download files from server
    # filename = object_name.file.name.split('/')[-1]
    # response = HttpResponse(object_name.file, content_type='text/plain')
    # response['Content-Disposition'] = 'attachment; filename=%s' % filename
    #
    # return response
    print("got hre")
    results = image.objects.all()
    return JsonResponse({"asdfsd":"ASDAa"})
