# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render, render_to_response, get_object_or_404, redirect
from django.http import HttpResponse, HttpResponseRedirect, JsonResponse
from .models import *
from PIL import Image
from django.conf import settings
from django.core.files.storage import FileSystemStorage
import numpy as np
import tensorflow as tf
# from seefood-core-ai import find_food.py as foodMethod
# from startup import setup
def tempai():
    return [[1.3, 1.223]]
 # sess = tempai()

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
        print( request.FILES)
        # myfile = request.FILES['myfile']
        # fs = FileSystemStorage()
        # filename = fs.save(myfile.name, myfile)
        # uploaded_file_url = fs.url(filename)
        # return JsonResponse({'didItWork':"it worked!"})
        return photoCheck(request)
    return render(request, 'index.html')

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
        # im_resized.save(filename)
        #need to check validity here
        #need to send to api here

        newImageObject.positiveCertainty = scores[0][0]
        newImageObject.negativeCertainty = scores[0][1]
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
