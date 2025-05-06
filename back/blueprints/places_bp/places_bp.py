from flask_smorest import Blueprint, abort
from connection import getConnection
from model.places import Place
from model.users import User
from blueprints.places_bp.schemas.places_output_schemas import *
from blueprints.places_bp.schemas.places_input_schemas import *

bp = Blueprint('places_bp', __name__, url_prefix='/places')

@bp.route('/get_all')
@bp.response(200, GetAll_OutputSchema)
def get_all_places():
    session = getConnection()
    places = session.query(Place).all()
    return {"places": places}

@bp.route('/get')
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, PlaceSchema)
def get_place(args):
    id = args.get('id')
    print('id is', id)
    session = getConnection()
    place = session.query(Place).filter(Place.id == id).first()
    if place is None:
        abort(404, message="no place with this id was found")
    return place

@bp.route('/create', methods=['POST'])
@bp.arguments(Create_InputSchema)
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

def update_place():
    ...

def delete_place():
    ...