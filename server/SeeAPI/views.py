# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render, render_to_response, get_object_or_404, redirect
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse

# Create your views here.
def homepageView(request):
    return render (request, "index.html")
