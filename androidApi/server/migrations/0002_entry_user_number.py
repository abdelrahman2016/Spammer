# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import django.core.validators


class Migration(migrations.Migration):

    dependencies = [
        ('server', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='entry',
            name='user_number',
            field=models.CharField(default=12, max_length=256, validators=[django.core.validators.RegexValidator(b'^[0-9]*$', b'Please Enter a Valid Phone Number')]),
            preserve_default=False,
        ),
    ]
