from django.test import TestCase
from rest_framework.test import APIClient
from .models import Entry

# Create your tests here.
class APITest(TestCase):

    def test_get_api(self):
        response = self.client.get('/spammed/')
        self.assertEqual(response.status_code,200)

    def test_add_number(self):
        data = {"username":"user1","spammed_number": 12345, "user_number":123}
        url = '/spammed/'
        response = self.client.post(url,data)
        self.assertEqual(response.status_code, 201)
