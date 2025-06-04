from flask import Flask, jsonify, request
from flask_smorest import Api
import requests
from flask_cors import CORS
import connection
import sys
from model import model
from model.users import User
from model.places import Place
from model.rentings import Renting
from model.guest_reviews import GuestReview
from model.place_images import PlaceImage
from model.place_reviews import PlaceReview
from model.favorite_lists import FavoriteList
from model.contains import Contain
from blueprints.users_bp import users_bp
from blueprints.places_bp import places_bp
from blueprints.place_imgs_bp import place_imgs_bp
from blueprints.guest_reviews_bp import guest_reviews_bp
from blueprints.renting_bp import renting_bp
from blueprints.place_reviews_bp import place_reviews_bp
from blueprints.favorite_lists_bp import favorite_lists_bp
import time

# Basic setup
while not connection.testConnection():
    print("Connection to the DB was not possible. Trying again after a brief delay")
    time.sleep(2)
session = connection.getConnection()
model.createTables()

# Api setup
app = Flask(__name__)
app.config['API_TITLE'] = 'FireBnb API'
app.config['API_VERSION'] = 'v1'
app.config['OPENAPI_VERSION'] = '3.1.0'
CORS(app)

api = Api(app)

# Blueprints
api.register_blueprint(users_bp.bp)
api.register_blueprint(places_bp.bp)
api.register_blueprint(place_imgs_bp.bp)
api.register_blueprint(guest_reviews_bp.bp)
api.register_blueprint(renting_bp.bp)
api.register_blueprint(place_reviews_bp.bp)
api.register_blueprint(favorite_lists_bp.bp)

# Endpoints
@app.route('/')
def helloWorld():
    result = {'Hey bro': 'This is working my friend'}
    return jsonify(result), 200

# Run app
if __name__ == '__main__':
    # app.run()
    app.run(host='0.0.0.0')

connection.closeConnection()