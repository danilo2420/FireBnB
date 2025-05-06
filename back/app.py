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

# Basic setup
if not connection.testConnection():
    print("Connection to the DB was not possible. Exiting program...")
    sys.exit()
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

# Endpoints
@app.route('/')
def helloWorld():
    return jsonify('Hello'), 200

# Run app
if __name__ == '__main__':
    app.run()

connection.closeConnection()