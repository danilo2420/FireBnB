from flask_smorest import Blueprint, abort
from connection import getConnection
from blueprints.general_input_output_schemas.general_input_schemas import *
from blueprints.general_input_output_schemas.general_output_schemas import *
from blueprints.favorite_lists_bp.schemas.favorite_lists_input_schemas import *
from blueprints.favorite_lists_bp.schemas.favorite_lists_output_schemas import *
from model.favorite_lists import FavoriteList
from model.users import User

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
            favorite_lists.extend(getFavoriteListsForUser(val))
        case _:
            print('Problem with get endpoint')

    return {'favorite_lists': favorite_lists}
    
def getFavoriteListById(id):
    session = getConnection()

    favorite_list = session.query(FavoriteList).filter(FavoriteList.id == id).first()
    if favorite_list is None:
        abort(404, message='no favorite list was found with this id')

    return favorite_list

def getFavoriteListsForUser(user_id):
    session = getConnection()

    user = session.query(User).filter(User.id == user_id).first()
    if user is None:
        abort(404, message='no user was found with this id')
    
    return user.favorite_lists

@bp.route('get_places')
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, GetPlaces_OutputSchema)
def get_favorite_lists(args):
    id = args.get('id')
    session = getConnection()

    favorite_list = session.query(FavoriteList).filter(FavoriteList.id == id).first()
    if favorite_list is None:
        abort(404, message='no favorite list was found with this id')

    print(favorite_list.places)

    return {'places': favorite_list.places}

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