# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.contrib import admin
from .models import *

admin.site.register(image)
class FileAdmin(admin.ModelAdmin):
    list_display = ['fileLink']
    readonly_fields = ['fileLink']
