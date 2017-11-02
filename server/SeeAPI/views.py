# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render, render_to_response, get_object_or_404, redirect
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse
from .models import *
import Image


def homepageView(request):
    return render (request, "index.html")

def photoCheck(request):
    image.objects.create(photo = request.FILES['file'])
    im = Image.open(request.FILES['file'])
    size = [200,200]
    im_resized = im.resize(size, Image.ANTIALIAS)
    # TODO:fix this so that it will be acutal resolution and file names and stuff
    im_resized.save('SOMETHINGTODOWITHTHEPHOTOHERE.png', "PNG")

    #need to check validity here
    #need to send to api here
    #need to send to database
    return True

def photoQeury(request, shortcode = None):
    results = image.objects.filter(pk>shortcode).filter(pk<shortcode+31)
    return JsonResponse(results, status=201)
