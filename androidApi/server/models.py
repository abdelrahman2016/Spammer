from django.db import models
from django.core.validators import RegexValidator

# Create your models here.
class Entry(models.Model):
    """model that represents an Entry
    Author: Mohamed Saieed
    """
    username = models.CharField(blank=False,null = False ,max_length=100)
    numeric = RegexValidator(
        r'^[0-9]*$', 'Please Enter a Valid Phone Number')
    user_number = models.CharField(max_length=256, null=False, blank=False, validators=[numeric])
    spammed_number = models.CharField(
        max_length=256, null=False, blank=False, validators=[numeric])
