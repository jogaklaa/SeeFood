# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render, render_to_response, get_object_or_404, redirect
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse
from .models import *


def homepageView(request):
    return render (request, "index.html")

def photoCheck(request):
    return True

def photoQeury(request, shortcode = None):
    results = image.objects.get(pk>shortcode).get(pk<shortcode+31)
    return JsonResponse(results, status=201)
