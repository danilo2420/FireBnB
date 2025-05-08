from flask_smorest import Blueprint, abort
from blueprints.guest_reviews_bp.schemas.guest_reviews_input_schemas import *
from blueprints.guest_reviews_bp.schemas.guest_reviews_output_schemas import *
from blueprints.general_input_output_schemas.general_input_schemas import *
from blueprints.general_input_output_schemas.general_output_schemas import *
from connection import getConnection
from model.guest_reviews import GuestReview
from model.schemas.guestReviewSchema import GuestReviewSchema
from model.users import User

bp = Blueprint('guest_reviews_bp', __name__, url_prefix='/guest_reviews')

@bp.route('/get')
@bp.arguments(GuestReviewGet_InputSchema, location='query')
@bp.response(200, GuestReviewGetAll_OutputSchema)
def get_guest_reviews(args):
    if len(args) == 0:
        return getAllGuestReviews()
    
    key = list(args.keys())[0]
    val = args.get(key)

    match key:
        case 'id':
            return getGuestReviewById(val)
        case 'host_id':
            return getHostsGuestReviews(val)
        case 'guest_id':
            return getGuestsGuestReviews(val)
        case _:
            print('Error with the input arguments')
    
    abort(400)

def getAllGuestReviews():
    session = getConnection()
    guest_reviews = session.query(GuestReview).all()
    return {'guest_reviews': guest_reviews}

def getGuestReviewById(id):
    session = getConnection()

    guest_review = session.query(GuestReview).filter(GuestReview.id == id).first()
    if guest_review is None:
        abort(404, message='no guest review was found with this id')
    
    return {'guest_reviews': [guest_review]} 

def getHostsGuestReviews(host_id):
    session = getConnection()

    # Check host exists
    host = session.query(User).filter(User.id == host_id).first()
    if host is None:
        abort(404, message='host user was not found with this id')
    
    return {'guest_reviews': host.host_reviews}

def getGuestsGuestReviews(guest_id):
    session = getConnection()

    # Check host exists
    guest = session.query(User).filter(User.id == guest_id).first()
    if guest is None:
        abort(404, message='guest user was not found with this id')
    
    return {'guest_reviews': guest.guest_reviews}

@bp.route('/create', methods=['POST'])
@bp.arguments(GuestReviewCreate_InputSchema)
@bp.response(200, Success_OutputSchema)
def create_guest_review(args):
    session = getConnection()

    guest_review = GuestReview(
        host_id = args.get('host_id'),
        guest_id = args.get('guest_id'),
        date = args.get('date'),
        stars = args.get('stars'),
        description = args.get('description')
    )

    session.add(guest_review)
    session.commit()

    return {'message': 'success creating guest review'}

@bp.route('/update', methods=['PUT'])
@bp.arguments(GuestReviewUpdate_InputSchema)
@bp.response(200, Success_OutputSchema)
def update_guest_review(args):
    id = args.get('id')
    session = getConnection()

    guest_review = session.query(GuestReview).filter(GuestReview.id == id).first()
    if guest_review is None:
        abort(404, message="no guest review was found with this id")

    guest_review.date = args.get('date', guest_review.date)
    guest_review.stars = args.get('stars', guest_review.stars)
    guest_review.description = args.get('description', guest_review.description)

    session.commit()

    return {'message': 'Guest review was updated successfully'}

@bp.route('/delete', methods=['DELETE'])
@bp.arguments(GuestReviewDelete_InputSchema, location='query')
@bp.response(200, Success_OutputSchema)
def delete_guest_review(args):
    key = list(args.keys())[0]
    val = args.get(key)

    match key:
        case 'id':
            return deleteGuestReviewById(val)
        case 'host_id':
            return deleteHostsGuestReviews(val)
        case 'guest_id':
            return deleteGuestsGuestReviews(val)
        case _:
            print('Error with the input arguments')
    
    abort(404)

def deleteGuestReviewById(id):
    session = getConnection()

    guest_review = session.query(GuestReview).filter(GuestReview.id == id).first()
    if guest_review is None:
        abort(404, message='no guest review was found with this id')
    
    session.delete(guest_review)
    session.commit()

    return {'message': 'guest review was deleted successfully'}

def deleteHostsGuestReviews(host_id):
    session = getConnection()

    # Check host exists
    host = session.query(User).filter(User.id == host_id).first()
    if host is None:
        abort(404, message='host user was not found with this id')
    
    for review in host.host_reviews:
        session.delete(review)
    
    session.commit()
    
    return {'message': 'guest reviews were deleted successfully'}

def deleteGuestsGuestReviews(guest_id):
    session = getConnection()

    # Check host exists
    guest = session.query(User).filter(User.id == guest_id).first()
    if guest is None:
        abort(404, message='guest user was not found with this id')

    for review in guest.guest_reviews:
        session.delete(review)

    session.commit()

    return {'message': 'guest reviews were deleted successfully'}
