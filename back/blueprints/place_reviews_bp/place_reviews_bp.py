from flask_smorest import Blueprint, abort
from connection import getConnection
from blueprints.general_input_output_schemas.general_input_schemas import *
from blueprints.general_input_output_schemas.general_output_schemas import *
from blueprints.place_reviews_bp.schemas.place_reviews_input_schemas import *
from blueprints.place_reviews_bp.schemas.place_reviews_output_schemas import *
from model.place_reviews import PlaceReview

bp = Blueprint('place_reviews_bp', __name__, url_prefix='/place_reviews')

@bp.route('/get')
@bp.arguments(Get_InputSchema, location='query')
@bp.response(200, GetAll_OutputSchema)
def get_place_reviews(args):
    if len(args) == 0:
        return {'place_reviews': getAllPlaceReviews()}
    '''
    all
    id
    guest's
    place's
    '''
    ...

def getAllPlaceReviews():
    session = getConnection()
    place_reviews = session.query(PlaceReview).all()
    return place_reviews


@bp.route('/create')
def create_place_reviews():
    ...


@bp.route('/update')
def update_place_reviews():
    ...


@bp.route('/delete')
def delete_place_reviews():
    ...