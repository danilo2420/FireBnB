from flask_smorest import Blueprint, abort
from blueprints.place_imgs_bp.schemas.place_imgs_input_schemas import *
from blueprints.place_imgs_bp.schemas.place_imgs_output_schemas import *
from connection import getConnection
from model.place_images import PlaceImage
from model.schemas.placeImgSchema import PlaceImgSchema
from model.places import Place

bp = Blueprint('place_imgs_bp', __name__, url_prefix='/place_imgs')

@bp.route('/get_all')
@bp.response(200, GetAll_OutputSchema)
def get_all_place_imgs():
    session = getConnection()
    place_imgs = session.query(PlaceImage).all()
    return {'place_imgs': place_imgs}

@bp.route('/get')
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, PlaceImgSchema)
def get_place_img(args):
    id = args.get('id')
    session = getConnection()

    place_img = session.query(PlaceImage).filter(PlaceImage.id == id).first()
    if place_img is None:
        abort(404, message='no place image was found with this id')

    return place_img

# Return images for a particular place (given its id)
@bp.route('get_imgs_for_place')
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, GetImgsForPlace_OutputSchema)
def get_place_imgs_for_place(args):
    id = args.get('id')
    session = getConnection()

    # Check place exists
    place = session.query(Place).filter(Place.id == id).first()
    if place is None:
        abort(404, message='no place was found with this id')
    
    return {'place_imgs': place.images}


@bp.route('/create', methods=['POST'])
@bp.arguments(Create_InputSchema)
@bp.response(200, Success_OutputSchema)
def create_place_img(args):
    session = getConnection()

    # Check if place exists
    place = session.query(Place).filter(Place.id == args.get('place_id')).first()
    if place is None:
        abort(404, message='There was no place with this id')

    place_img = PlaceImage(
        place_id = args.get('place_id'),
        title = args.get('title'),
        img = args.get('img')
    )

    session.add(place_img)
    session.commit()

    return {'message': 'Image created successfully'}

    
@bp.route('/update', methods=['PUT'])
@bp.arguments(Update_InputSchema)
@bp.response(200, Success_OutputSchema)
def update_place_img(args):
    id = args.get('id')
    session = getConnection()

    place_img = session.query(PlaceImage).filter(PlaceImage.id == id).first()
    if place_img is None:
        abort(404, message='no image was found with this id')
    
    place_img.title = args.get('title', place_img.title)
    place_img.img = args.get('img', place_img.img)
    session.commit()

    return {'message': 'image updated successfully'}

@bp.route('/delete', methods=['DELETE'])
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, Success_OutputSchema)
def delete_place_img(args):
    id = args.get('id')
    session = getConnection()

    place_img = session.query(PlaceImage).filter(PlaceImage.id == id).first()
    if place_img is None:
        abort(404, message='no image was found with this id')

    session.delete(place_img)
    session.commit()

    return {'message': 'image deleted successfully'}

@bp.route('/delete_imgs_for_place', methods=['DELETE'])
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, Success_OutputSchema)
def delete_place_imgs_for_place(args):
    id = args.get('id')
    session = getConnection()

    place = session.query(Place).filter(Place.id == id).first()
    if place is None:
        abort(404, message='no place was found with this id')

    for img in place.images:
        session.delete(img)
    
    session.commit()

    return {'message': 'place\'s images deleted successfully'}