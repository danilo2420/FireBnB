from flask_smorest import Blueprint, abort
from blueprints.guest_reviews_bp.schemas.guest_reviews_input_schemas import *
from blueprints.guest_reviews_bp.schemas.guest_reviews_output_schemas import *
from connection import getConnection
from model.guest_reviews import GuestReview
from model.schemas.guestReviewSchema import GuestReviewSchema
from model.users import User

bp = Blueprint('guest_reviews_bp', __name__, url_prefix='/guest_reviews')

# R
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

# C

@bp.route('/create', methods=['POST'])
def create_guest_review():
    ...

# U
@bp.route('/update', methods=['PUT'])
def update_guest_review():
    ...

# D
@bp.route('/delete', methods=['DELETE'])
def delete_guest_review():
    ...