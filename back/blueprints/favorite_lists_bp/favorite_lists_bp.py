from flask_smorest import Blueprint, abort
from connection import getConnection
from blueprints.general_input_output_schemas.general_input_schemas import *
from blueprints.general_input_output_schemas.general_output_schemas import *
from blueprints.favorite_lists_bp.schemas.favorite_lists_input_schemas import *
from blueprints.favorite_lists_bp.schemas.favorite_lists_output_schemas import *
from model.favorite_lists import FavoriteList

bp = Blueprint('favorite_lists_bp', __name__, url_prefix='/favorite_lists')

@bp.route('get')
@bp.arguments(Get_InputSchema, location='query')
@bp.response(200, GetAll_OutputSchema)
def get_favorite_lists(args):
    key = list(args.keys())[0]
    val = args.get(key)

    favorite_lists = []

    match key:
        case 'id':
            favorite_lists.append(getFavoriteListById(val))
        case 'user_id':
            ...
        case _:
            print('Problem with get endpoint')

    return {'favorite_lists': favorite_lists}
    
def getFavoriteListById(id):
    session = getConnection()

    favorite_list = session.query(FavoriteList).filter(FavoriteList.id == id).first()
    if favorite_list is None:
        abort(404, message='no favorite list was found with this id')

    return favorite_list

'''
    Get one by id
    Get user's lists
'''

@bp.route('create', methods=['CREATE'])
def create_favorite_lists():
    ...

## Add place to list too

@bp.route('update', methods=['UPDATE'])
def update_favorite_lists():
    ...

@bp.route('delete', methods=['DELETE'])
def delete_favorite_lists():
    ...

## Remove place from list too