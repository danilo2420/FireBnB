from flask_smorest import Blueprint, abort
from blueprints.place_imgs_bp.schemas.place_imgs_input_schemas import *
from blueprints.place_imgs_bp.schemas.place_imgs_output_schemas import *
from connection import getConnection
from model.place_images import PlaceImage

bp = Blueprint('place_imgs_bp', __name__, url_prefix='/place_imgs')

@bp.route('/get_all')
@bp.response(200, GetAll_OutputSchema)
def get_all_place_imgs():
    session = getConnection()
    place_imgs = session.query(PlaceImage).all()
    return {'place_imgs': place_imgs}

@bp.route('/get')
def get_place_img():
    ...

@bp.route('/create', methods=['POST'])
def create_place_img():
    ...

@bp.route('/update', methods=['PUT'])
def update_place_img():
    ...

@bp.route('/delete', methods=['DELETE'])
def delete_place_img():
    ...
