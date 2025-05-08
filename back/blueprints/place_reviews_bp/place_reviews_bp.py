from flask_smorest import Blueprint, abort
from connection import getConnection
from blueprints.general_input_output_schemas.general_input_schemas import *
from blueprints.general_input_output_schemas.general_output_schemas import *
from blueprints.place_reviews_bp.schemas.place_reviews_input_schemas import *
from blueprints.place_reviews_bp.schemas.place_reviews_output_schemas import *
from model.place_reviews import PlaceReview
from model.places import Place
from model.users import User

bp = Blueprint('place_reviews_bp', __name__, url_prefix='/place_reviews')

@bp.route('/get')
@bp.arguments(Get_InputSchema, location='query')
@bp.response(200, PlaceReviewGetAll_OutputSchema)
def get_place_reviews(args):
    # Return all
    if len(args) == 0:
        place_reviews = getAllPlaceReviews()
        for p in place_reviews:
            print(p.description)
        return {'place_reviews': place_reviews}
    
    # Other cases
    key = list(args.keys())[0]
    val = args.get(key)
    place_reviews = []
    match key:
        case 'id':
            place_reviews.append(getPlaceReviewById(val))
        case 'guest_id':
            place_reviews.extend(getPlaceReviewForGuest(val))
        case 'place_id':
            place_reviews.extend(getPlaceReviewForPlace(val))
        case _:
            print('There was a problem with the URL query arguments')

    return {'place_reviews': place_reviews}

def getAllPlaceReviews():
    session = getConnection()
    place_reviews = session.query(PlaceReview).all()
    return place_reviews

def getPlaceReviewById(id):
    session = getConnection()

    place_review = session.query(PlaceReview).filter(PlaceReview.id == id).first()
    if place_review is None:
        abort(404, message='no place review was found with this id')
    
    return place_review

def getPlaceReviewForGuest(guest_id):
    session = getConnection()

    guest = session.query(User).filter(User.id == guest_id).first()
    if guest is None:
        abort(404, message='no user was found with this id')
    
    return guest.place_reviews

def getPlaceReviewForPlace(place_id):
    session = getConnection()

    place = session.query(Place).filter(Place.id == place_id).first()
    if place is None:
        abort(404, message='no place was found with this id')
    
    return place.reviews

@bp.route('/create', methods=['POST'])
@bp.arguments(PlaceReviewCreate_InputSchema)
@bp.response(200, Success_OutputSchema)
def create_place_reviews(args):
    session = getConnection()

    place_review = PlaceReview(
        guest_id = args.get('guest_id'),
        place_id = args.get('place_id'),
        date = args.get('date'),
        description = args.get('description'),
        stars = args.get('stars')
    )

    session.add(place_review)
    session.commit()

    return {'message': 'place review created successfully'}


@bp.route('/update', methods=['PUT'])
@bp.arguments(PlaceReviewUpdate_InputSchema)
@bp.response(200, Success_OutputSchema)
def update_place_reviews(args):
    session = getConnection()
    id = args.get('id')

    place_review = session.query(PlaceReview).filter(PlaceReview.id == id).first()
    if place_review is None:
        abort(404, message='no place review was found with this id')

    place_review.date = args.get('date', place_review.date)
    place_review.description = args.get('description', place_review.description)
    place_review.stars = args.get('stars', place_review.stars)

    session.commit()

    return {'message': 'place review updated successfully'}

@bp.route('/delete', methods=['DELETE'])
@bp.arguments(PlaceReviewDelete_InputSchema, location='query')
@bp.response(200, Success_OutputSchema)
def delete_place_reviews(args):
    session = getConnection()
    key = list(args.keys())[0]
    val = args.get(key)

    match key:
        case 'id':
            deletePlaceReviewById(session, val)
        case 'guest_id':
            deletePlaceReviewsForGuest(session, val)
        case 'place_id':
            deletePlaceReviewsForPlace(session, val)
        case _:
            print('There was a problem with the URL query arguments')

    return {'message': 'places were deleted successfully'}

def deletePlaceReviewById(session, id):
    place_review = session.query(PlaceReview).filter(PlaceReview.id == id).first()
    if place_review is None:
        abort(404, message='no place review was found with this id')
    
    session.delete(place_review)
    session.commit()

def deletePlaceReviewsForGuest(session, guest_id):
    guest = session.query(User).filter(User.id == guest_id).first()
    if guest is None:
        abort(404, message='no user was found with this id')

    for place_review in guest.place_reviews:
        session.delete(place_review)
    session.commit()

def deletePlaceReviewsForPlace(session, place_id):
    place = session.query(Place).filter(Place.id == place_id).first()
    if place is None:
        abort(404, message='no place was found with this id')

    for place_review in place.reviews:
        session.delete(place_review)
    session.commit()
