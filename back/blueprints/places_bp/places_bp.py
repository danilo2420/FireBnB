from flask_smorest import Blueprint, abort

bp = Blueprint('places_bp', __name__, url_prefix='/places')

def get_all_places():
    ...

def get_place():
    ...

def create_place():
    ...

def update_place():
    ...

def delete_place():
    ...