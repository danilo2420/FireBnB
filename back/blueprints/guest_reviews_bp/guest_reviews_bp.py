from flask_smorest import Blueprint, abort
from blueprints.guest_reviews_bp.schemas.guest_reviews_input_schemas import *
from blueprints.guest_reviews_bp.schemas.guest_reviews_output_schemas import *
from connection import getConnection
from model.guest_reviews import GuestReview

bp = Blueprint('guest_reviews_bp', __name__, url_prefix='/guest_reviews')

# R
@bp.route('/get_all')
@bp.response(200, GetAll_OutputSchema)
def get_all_guest_reviews():
    session = getConnection()
    guest_reviews = session.query(GuestReview).all()
    return {'guest_reviews': guest_reviews}


@bp.route('/get')
def get_guest_review():
    ...

## Get all guest's reviews

## Get all host's reviews

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