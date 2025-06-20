from flask_smorest import Blueprint, abort
from connection import getConnection
from model.places import Place
from model.users import User
from model.place_images import PlaceImage
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

@bp.route('/getWithImage')
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, PlaceWithImage_OutputSchema)
def get_place_with_image(args):
    place_id = args.get('id')
    session = getConnection()

    place = session.query(Place).filter(Place.id == place_id).first()
    if place is None:
        abort(400, message="place does not exist")
    
    image = session.query(PlaceImage).filter(PlaceImage.place_id == place_id).first()
    
    return {
        "place": place,
        "image": image
    }

@bp.route('/getWithImage/all')
@bp.response(200, PlaceWithImageAll_OutputSchema)
def get_place_with_image():
    session = getConnection()

    places = session.query(Place).all()

    arr = []
    for place in places:
        image = session.query(PlaceImage).filter(PlaceImage.place_id == place.id).first()
        item = {
            "place": place,
            "image": image
        }
        arr.append(item)

    return {"places_with_img": arr}

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
        owner_id = args.get('owner_id'),
        name = args.get('name'),
        type = args.get('type'),
        description = args.get('description'),
        price_per_night = args.get('price_per_night'),
        stars = args.get('stars')
    )

    session.add(place)
    session.commit()

    return {'message': 'success'}

@bp.route('/createWithImage', methods=['POST'])
@bp.arguments(PlaceWithImage_OutputSchema)
@bp.response(200, Success_OutputSchema)
def create_place(args):
    owner_id = args.get('place').get('owner_id')
    session = getConnection()

    # Check owner exists
    owner = session.query(User).filter(User.id == owner_id).first()
    if owner is None:
        abort(400, message="owner user does not exist")

    place_data = args.get('place')
    image_data = args.get('image')

    # Create place
    place = Place(
        owner_id = place_data.get('owner_id'),
        name = place_data.get('name'),
        type = place_data.get('type'),
        description = place_data.get('description'),
        price_per_night = place_data.get('price_per_night'),
        stars = place_data.get('stars')
    )

    if image_data is not None:
        image = PlaceImage(
            title = image_data.get('title'),
            img = image_data.get('img')
        )
        place.images.append(image)

    session.add(place)
    session.commit()

    return {'message': 'success'}


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