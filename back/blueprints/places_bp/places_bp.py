from flask_smorest import Blueprint, abort
from connection import getConnection
from model.places import Place
from model.users import User
from blueprints.places_bp.schemas.places_output_schemas import *
from blueprints.places_bp.schemas.places_input_schemas import *
from blueprints.general_input_output_schemas.general_input_schemas import *
from blueprints.general_input_output_schemas.general_output_schemas import *

bp = Blueprint('places_bp', __name__, url_prefix='/places')

@bp.route('/get')
@bp.arguments(IdOptional_InputSchema, location='query')
@bp.response(200, PlaceGet_OutputSchema)
def get_places(args):
    if len(args) == 0:
        return getAllPlaces()
    
    id = args.get('id')
    return getPlace(id)

def getAllPlaces():
    session = getConnection()
    places = session.query(Place).all()
    return {"places": places}

def getPlace(id):
    session = getConnection()
    place = session.query(Place).filter(Place.id == id).first()
    if place is None:
        abort(404, message="no place with this id was found")
    return {'places': [place]}

@bp.route('/create', methods=['POST'])
@bp.arguments(PlaceCreate_InputSchema)
@bp.response(200, Success_OutputSchema)
def create_place(args):
    owner_id = args.get('owner_id')
    session = getConnection()

    # Check owner exists
    owner = session.query(User).filter(User.id == owner_id).first()
    if owner is None:
        abort(400, message="owner user does not exist")

    # Create place
    place = Place(
        id = args.get('id'),
        owner_id = args.get('owner_id'),
        name = args.get('name'),
        type = args.get('type'),
        description = args.get('description'),
        price_per_night = args.get('price_per_night'),
        stars = args.get('stars')
    )

    session.add(place)
    session.commit()

    return {'message': 'place was created successfully'}

@bp.route('update', methods=['PUT'])
@bp.arguments(PlaceUpdate_InputSchema)
@bp.response(200, Success_OutputSchema)
def update_place(args):
    id = args.get('id')
    session = getConnection()

    # Check place exists
    place = session.query(Place).filter(Place.id == id).first()
    if place is None:
        abort(404, message='no place with this id was found')

    # Update placelaces WHERE id = 3;
    place.name = args.get('name', place.name)
    place.type = args.get('type', place.type)
    place.description = args.get('description', place.description)
    place.price_per_night = args.get('price_per_night', place.price_per_night)
    place.stars = args.get('stars', place.stars)

    session.commit()

    return {'message': 'success'}

@bp.route('/delete', methods=['DELETE'])
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, Success_OutputSchema)
def delete_place(args):
    id = args.get('id')
    session = getConnection()

    # Check place exists
    place = session.query(Place).filter(Place.id == id).first()
    if place is None:
        abort(404, message='no place with this id was found')
    
    # Delete place
    session.delete(place)
    session.commit()

    return {'message': 'success'}