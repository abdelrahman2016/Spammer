from django.conf.urls import url, include
from . import views
from rest_framework.routers import DefaultRouter

router = DefaultRouter()
router.register(r'spammed', views.EntryViewSet)

urlpatterns = [
    url(r'^', include(router.urls)),]
