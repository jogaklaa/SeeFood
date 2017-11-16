# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

# Create your models here.

class image(models.Model):
    positiveCertainty = models.DecimalField(decimal_places=15,max_digits=20, null = True)
    negativeCertainty = models.DecimalField(decimal_places=15,max_digits=20, null = True)
    photo = models.ImageField(upload_to='photos', null = True)
