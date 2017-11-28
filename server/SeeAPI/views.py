# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render, render_to_response, get_object_or_404, redirect
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse, HttpResponseNotFound
from .models import *
from PIL import Image
from django.conf import settings
from django.core.files.storage import FileSystemStorage
from django.views.decorators.csrf import csrf_exempt
import numpy as np
import base64
import tensorflow as tf


sess = tf.Session()
saver = tf.train.import_meta_graph('saved_model/model_epoch5.ckpt.meta')
saver.restore(sess, tf.train.latest_checkpoint('saved_model/'))
graph = tf.get_default_graph()
x_input = graph.get_tensor_by_name('Input_xn/Placeholder:0')
keep_prob = graph.get_tensor_by_name('Placeholder:0')
class_scores = graph.get_tensor_by_name("fc8/fc8:0")
print("AI has been loaded, party hard!")



def homepageView(request):
    if request.method == 'POST' and request.FILES['myfile']:
        # for newImage in request.FILES:
        #     newImageObject = image.objects.create(photo = request.FILES[newImage], positiveCertainty = 1.5, negativeCertainty = .3)
        #     newImageObject.save()
        # return JsonResponse({"item":"it worked!"})
        return photoCheck(request)
    return render(request, 'index.html')

@csrf_exempt
def photoCheck(request):
    fs = FileSystemStorage()
    values = {}
    for newImage in request.FILES:
        newImageObject = image.objects.create(photo = request.FILES[newImage])
        im = Image.open(request.FILES[newImage]).convert('RGB')
        im = im.resize((227, 227), Image.BILINEAR)
        img_tensor = [np.asarray(im, dtype=np.float32)]
        scores = sess.run(class_scores, {x_input: img_tensor, keep_prob: 1.}).tolist()
        filename = request.FILES[newImage].name
        newImageObject.positiveCertainty = scores[0][0]
        newImageObject.negativeCertainty = scores[0][1]
        newImageObject.save()
        print({"photo":newImageObject.photo, "positive":newImageObject.positiveCertainty, "negative":newImageObject.negativeCertainty})
        values[filename] = {"positive":newImageObject.positiveCertainty, "negative":newImageObject.negativeCertainty}
    return JsonResponse(values)

@csrf_exempt
def photoQeury(request, pk = 10):
    data = {}
    for i in range(0,int(pk)):
        if i >= len(image.objects.all()):
            break
        photo = image.objects.all().order_by('-pk')[i]
        # dictionary =  {"photo":base64.b64encode(open(photo.photo, "rb").read())}
        dictionary ={"photo":"http://34.234.229.114:8000/media/"+photo.photo.name}
        dictionary['positive'] =photo.positiveCertainty
        dictionary['negative'] =photo.negativeCertainty
        data[i] = dictionary
    return JsonResponse(data)

def photoview(request, pk = None):
    # image_data = open(image.objects.get(pk=pk).photo, "rb").read()
    return HttpResponse(image.objects.get(pk=pk).photo, content_type="image/png")
