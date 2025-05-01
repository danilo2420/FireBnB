from flask import Flask, jsonify, request
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

# Basic setup
if not connection.testConnection():
    print("Connection to the DB was not possible. Exiting program...")
    sys.exit()
model.createTables()
app = Flask(__name__)
CORS(app)
session = connection.getConnection()

# Endpoints
@app.route('/')
def helloWorld():
    return jsonify('Hello'), 200

## Users
@app.route('/users/get')
def get_user():
    try:
        id = request.args.get('id')

        # Exception handling
        ## Check id argument is not null
        if id is None:
            raise Exception('Id cannot be null')
        ## Check id argument is not empty
        if len(id) == 0:
            raise Exception('Id cannot be empty')
        ## Check id is an int - it throws ValueError if not 
        int(id)

        # Make query to database
        user = session.query(User).filter(User.id == id).first()

        # Check an user exists with the id
        if user is None:
            raise Exception('No user exists with this id')
        
        # Format result and send it
        result = getUserDict(user)
        return jsonify(result), 200
        
    except ValueError as e:
        return jsonify({'error': 'Id has to be an integer'}), 400
    except Exception as e:
        code = None
        if str(e) == 'No user exists with this id':
            code = 404
        else:
            code = 400
        return jsonify({'error': str(e)}), code

@app.route('/users/getAll')
def get_all_users():
    users = session.query(User)

    result = []
    for row in users:
        user = getUserDict(row)
        result.append(user)
    
    return jsonify(result), 200

def getUserDict(data):
    return {
        "id": data.id,
        "name": data.name,
        "lastName": data.lastName,
        "age": data.age,
        "nationality": data.nationality,
        "description": data.description,
        "profile_image": data.profile_image,
        "stars": data.stars
    }

@app.route('/users/create', methods=['POST'])
def create_user():
    try: 
        data = request.get_json()
        user = User(
            name = data.get('name', None),
            lastName = data.get('lastName', None),
            age = data.get('age', None),
            nationality = data.get('nationality', None),
            description = data.get('description', None),
            profile_image = data.get('profile_image', None),
            stars = data.get('stars', None)
        )
        session.add(user)
        session.commit()
        return jsonify({'message': 'user created successfully'}), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 400

@app.route('/users/update', methods=['PUT'])
def update_user():
    try:
        id = request.args.get('id')

        # Exception handling
        ## Check id argument is not null
        if id is None:
            raise Exception('Id cannot be null')
        ## Check id argument is not empty
        if len(id) == 0:
            raise Exception('Id cannot be empty')
        ## Check id is an int - it throws ValueError if not 
        int(id)

        # Make query to database
        user = session.query(User).filter(User.id == id).first()

        # Check an user exists with the id
        if user is None:
            raise Exception('No user exists with this id')

        # Update
        data = request.get_json()

        user.name = data.get('name', user.name)
        user.lastName = data.get('lastName', user.lastName)
        user.age = data.get('age', user.age)
        user.nationality = data.get('nationality', user.nationality)
        user.description = data.get('description', user.description)
        user.profile_image = data.get('profile_image', user.profile_image)
        user.stars = data.get('stars', user.stars)

        session.commit()

        # Format result and send it
        return jsonify({'message': 'user updated successfully'}), 200
        
    except ValueError as e:
        return jsonify({'error': 'Id has to be an integer'}), 400
    except Exception as e:
        code = None
        if str(e) == 'No user exists with this id':
            code = 404
        else:
            code = 400
        return jsonify({'error': str(e)}), code

@app.route('/users/delete', methods=['DELETE'])
def delete_user():
    try:
        id = request.args.get('id')

        # Exception handling
        ## Check id argument is not null
        if id is None:
            raise Exception('Id cannot be null')
        ## Check id argument is not empty
        if len(id) == 0:
            raise Exception('Id cannot be empty')
        ## Check id is an int - it throws ValueError if not 
        int(id)

        # Make query to database
        user = session.query(User).filter(User.id == id).first()

        # Check an user exists with the id
        if user is None:
            raise Exception('No user exists with this id')

        # Delete
        session.delete(user)
        session.commit()

        # Format result and send it
        return jsonify({'message': 'user deleted successfully'}), 200
        
    except ValueError as e:
        return jsonify({'error': 'Id has to be an integer'}), 400
    except Exception as e:
        code = None
        if str(e) == 'No user exists with this id':
            code = 404
        else:
            code = 400
        return jsonify({'error': str(e)}), code


## Places
@app.route('/places/get')
def get_place():
    try:
        id = request.args.get('id')

        # Exception handling
        ## Check id argument is not null
        if id is None:
            raise Exception('Id cannot be null')
        ## Check id argument is not empty
        if len(id) == 0:
            raise Exception('Id cannot be empty')
        ## Check id is an int - it throws ValueError if not 
        int(id)

        # Make query to database
        place = session.query(Place).filter(Place.id == id).first()

        # Check an place exists with the id
        if place is None:
            raise Exception('No user exists with this id')
        
        result = get_place_dict(place)

        return jsonify(result), 200
    
    except ValueError as e:
        return jsonify({'error': 'Id has to be an integer'}), 400
    except Exception as e:
        code = None
        if str(e) == 'No place exists with this id':
            code = 404
        else:
            code = 400
        return jsonify({'error': str(e)}), code

@app.route('/places/getAll')
def get_all_places():
    places = session.query(Place)

    result = []

    for row in places:
        place = get_place_dict(row)
        result.append(place)
    
    return jsonify(result), 200

def get_place_dict(data):
    return {
        'id': data.id,
        'name': data.name,
        'type': data.type,
        'price_per_night': data.price_per_night,
        'owner_id': data.owner_id
    }

'''
    id = Column(Integer, primary_key=True)
    name = Column(String)
    type = Column(Enum(PlaceCategory))
    price_per_night = Column(Float)
    
    # Relationships
    ## Owner relationship
    owner_id = Column(Integer, ForeignKey('users.id'))
'''

@app.route('/places/create', methods=['POST'])
def create_place():
    try:
        data = request.get_json()

        # A place should always have an owner
        owner_id = data.get('owner_id')

        if owner_id is None:
            raise Exception('owner_id cannot be null')
        
        response = requests.get(f'http://localhost:5000/users/get?id={owner_id}')
        if response.status_code != 200:
            raise Exception('no user with the owner_id provided exists')
        
        # Create and add place
        place = Place(
            name = data.get('name', None),
            type = data.get('type', None),
            price_per_night = data.get('price_per_night', None),
            owner_id = data.get('owner_id')
        )

        session.add(place)
        session.commit()

        return jsonify({'message': 'place created successfully'}), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 400

@app.route('/places/update', methods=['PUT'])
def update_place():
    try:
        id = request.args.get('id')

        ## Check id argument is not null
        if id is None:
            raise Exception('Id cannot be null')
        ## Check id argument is not empty
        if len(id) == 0:
            raise Exception('Id cannot be empty')
        ## Check id is an int - it throws ValueError if not 
        int(id)

        place = session.query(Place).filter(Place.id == id).first()
        if place is None:
            raise Exception('No place exists with this id')

        data = request.get_json()

        place.name = data.get('name', place.name)
        place.type = data.get('type', place.type)
        place.price_per_night = data.get('price_per_night', place.price_per_night)
        # owner_id cannot be updated

        session.commit()

        return jsonify({'message': 'place updated successfully'}), 200

    except ValueError as e:
        return jsonify({'error': 'Id has to be an integer'}), 400
    except Exception as e:
        code = None
        if str(e) == 'No place exists with this id':
            code = 404
        else:
            code = 400
        return jsonify({'error': str(e)}), code

@app.route('/places/delete', methods=['DELETE'])
def delete_place():
    try:
        id = request.args.get('id')

        # Exception handling
        ## Check id argument is not null
        if id is None:
            raise Exception('Id cannot be null')
        ## Check id argument is not empty
        if len(id) == 0:
            raise Exception('Id cannot be empty')
        ## Check id is an int - it throws ValueError if not 
        int(id)

        # Make query to database
        place = session.query(Place).filter(Place.id == id).first()

        # Check an user exists with the id
        if place is None:
            raise Exception('No place exists with this id')

        # Delete
        session.delete(place)
        session.commit()

        # Format result and send it
        return jsonify({'message': 'place deleted successfully'}), 200
        
    except ValueError as e:
        return jsonify({'error': 'Id has to be an integer'}), 400
    except Exception as e:
        code = None
        if str(e) == 'No place exists with this id':
            code = 404
        else:
            code = 400
        return jsonify({'error': str(e)}), code

# Run app
app.run(debug=True)
connection.closeConnection()