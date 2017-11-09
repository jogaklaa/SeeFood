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

    image.objects.create(photo = request.FILES['file'])
    im = Image.open(request.FILES['file'])
    # TODO: get actual file size
    size = [200,200]
    im_resized = im.resize(size, Image.ANTIALIAS)
    filename = "photos/"+request.FILES['file'].name[:request.FILES['file'].name.index('.')-1]+'resized.png'
    im_resized.save(filename)
    #need to check validity here
    #need to send to api here
    #need to send to database
    return JsonResponse({"work":"itworked"})

def photoQeury(request, shortcode = None):
    results = image.objects.filter(pk>shortcode).filter(pk<shortcode+31)
    return JsonResponse(results, status=201)
