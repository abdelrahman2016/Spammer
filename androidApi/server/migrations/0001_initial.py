# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import django.core.validators


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Entry',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('username', models.CharField(max_length=100)),
                ('spammed_number', models.CharField(max_length=256, validators=[django.core.validators.RegexValidator(b'^[0-9]*$', b'Please Enter a Valid Phone Number')])),
            ],
        ),
    ]
