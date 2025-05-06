from flask_smorest import Blueprint, abort
from connection import getConnection
from model.places import Place
from blueprints.places_bp.schemas.places_output_schemas import *

bp = Blueprint('places_bp', __name__, url_prefix='/places')

@bp.route('/get_all')
@bp.response(200, GetAll_OutputSchema)
def get_all_places():
    session = getConnection()
    places = session.query(Place).all()
    for p in places:
        print(p.name)
    return {"places": places}

def get_place():
    ...

def create_place():
    ...

def update_place():
    ...

def delete_place():
    ...