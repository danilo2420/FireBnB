from flask_smorest import Blueprint, abort
from blueprints.guest_reviews_bp.schemas.guest_reviews_input_schemas import *
from blueprints.guest_reviews_bp.schemas.guest_reviews_output_schemas import *
from connection import getConnection
from model.guest_reviews import GuestReview
from model.schemas.guestReviewSchema import GuestReviewSchema
from model.users import User

bp = Blueprint('guest_reviews_bp', __name__, url_prefix='/guest_reviews')

@bp.route('/get_all')
@bp.response(200, GetAll_OutputSchema)
def get_all_guest_reviews():
    session = getConnection()
    guest_reviews = session.query(GuestReview).all()
    return {'guest_reviews': guest_reviews}

@bp.route('/get')
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, GuestReviewSchema)
def get_guest_review(args):
    id = args.get('id')
    session = getConnection()

    guest_review = session.query(GuestReview).filter(GuestReview.id == id).first()
    if guest_review is None:
        abort(404, message='no guest review was found with this id')
    
    return guest_review 

## Get all host's reviews
@bp.route('/get_all_for_host')
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, GetAll_OutputSchema)
def get_guest_reviews_for_host(args):
    id = args.get('id')
    session = getConnection()

    # Check host exists
    host = session.query(User).filter(User.id == id).first()
    if host is None:
        abort(404, message='host user was not found with this id')
    
    return {'guest_reviews': host.host_reviews}

## Get all guest's reviews
@bp.route('/get_all_for_guest')
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, GetAll_OutputSchema)
def get_guest_reviews_for_guest(args):
    id = args.get('id')
    session = getConnection()

    # Check host exists
    guest = session.query(User).filter(User.id == id).first()
    if guest is None:
        abort(404, message='guest user was not found with this id')
    
    return {'guest_reviews': guest.guest_reviews}

@bp.route('/create', methods=['POST'])
@bp.arguments(Create_InputSchema)
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

# U
@bp.route('/update', methods=['PUT'])
@bp.arguments(Update_InputSchema)
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

# D
@bp.route('/delete', methods=['DELETE'])
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, Success_OutputSchema)
def delete_guest_review(args):
    id = args.get('id')
    session = getConnection()

    guest_review = session.query(GuestReview).filter(GuestReview.id == id).first()
    if guest_review is None:
        abort(404, message='no guest review was found with this id')

    session.delete(guest_review)
    session.commit()

    return {'message': 'Guest review was deleted successfully'}


# Delete for all hosts
@bp.route('/delete_hosts_guest_reviews', methods=['DELETE'])
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, Success_OutputSchema)
def delete_guest_review_for_host(args):
    id = args.get('id')
    session = getConnection()

    host = session.query(User).filter(User.id == id).first()
    if host is None:
        abort(404, message='no user was found with this id')
    
    for guest_review in host.host_reviews:
        session.delete(guest_review)
    session.commit()

    return {'message': 'host\'s guest reviews was deleted successfully'}

# Delete for all guests
@bp.route('/delete_guests_guest_reviews', methods=['DELETE'])
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, Success_OutputSchema)
def delete_guest_review_for_host(args):
    id = args.get('id')
    session = getConnection()

    guest = session.query(User).filter(User.id == id).first()
    if guest is None:
        abort(404, message='no user was found with this id')
    
    for guest_review in guest.guest_reviews:
        session.delete(guest_review)
    session.commit()

    return {'message': 'guest\'s guest reviews was deleted successfully'}