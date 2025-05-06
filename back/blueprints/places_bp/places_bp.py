from flask_smorest import Blueprint, abort
from connection import getConnection
from model.places import Place
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

def create_place():
    ...

def update_place():
    ...

def delete_place():
    ...