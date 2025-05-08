from flask_smorest import Blueprint, abort
from connection import getConnection
from blueprints.general_input_output_schemas.general_input_schemas import *
from blueprints.general_input_output_schemas.general_output_schemas import *
from blueprints.favorite_lists_bp.schemas.favorite_lists_input_schemas import *
from blueprints.favorite_lists_bp.schemas.favorite_lists_output_schemas import *
from model.favorite_lists import FavoriteList
from model.places import Place
from model.users import User

bp = Blueprint('favorite_lists_bp', __name__, url_prefix='/favorite_lists')

@bp.route('get')
@bp.arguments(FavoriteListGet_InputSchema, location='query')
@bp.response(200, FavoriteListGetAll_OutputSchema)
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


@bp.route('create', methods=['POST'])
@bp.arguments(FavoriteListCreate_InputSchema)
@bp.response(200, Success_OutputSchema)
def create_favorite_lists(args):
    session = getConnection()

    favorite_list = FavoriteList(
        user_id = args.get('user_id'),
        name = args.get('name')
    )

    session.add(favorite_list)
    session.commit()

    return {'message': 'favorite list created successfully'}

@bp.route('update', methods=['PUT'])
@bp.arguments(FavoriteListUpdate_InputSchema)
@bp.response(200, Success_OutputSchema)
def update_favorite_lists(args):
    session = getConnection()
    id = args.get('id')

    favorite_list = session.query(FavoriteList).filter(FavoriteList.id == id).first()
    if favorite_list is None:
        abort(404, message='no favorite list was found with this id')
    
    favorite_list.name = args.get('name', favorite_list.name)

    session.commit()

    return {'message': 'favorite list updated successfully'}

## Add place to list
@bp.route('add_place_to_list', methods=['PUT'])
@bp.arguments(LinkPlace_InputSchema)
@bp.response(200, Success_OutputSchema)
def add_place_to_list(args):
    session = getConnection()
    
    id = args.get('id')
    favorite_list = session.query(FavoriteList).filter(FavoriteList.id == id).first()
    if favorite_list is None:
        abort(404, message='no favorite list was found with this id')

    place_id = args.get('place_id')
    place = session.query(Place).filter(Place.id == place_id).first()
    if place is None:
        abort(404, message='no place was found with this id')

    if place in favorite_list.places:
        abort(400, message="place already exists in list")
        
    favorite_list.places.append(place)
    session.commit()

    return {'message': 'place added to list successfully'}

@bp.route('delete', methods=['DELETE'])
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, Success_OutputSchema)
def delete_favorite_lists(args):
    id = args.get('id')
    session = getConnection()

    favorite_list = session.query(FavoriteList).filter(FavoriteList.id == id).first()
    if favorite_list is None:
        abort(404, message='no favorite list was found with this id')

    session.delete(favorite_list)
    session.commit()

    return {'message': 'list deleted successfully'}

## Remove place from list
@bp.route('remove_place_from_list', methods=['PUT'])
@bp.arguments(LinkPlace_InputSchema)
@bp.response(200, Success_OutputSchema)
def add_place_to_list(args):
    session = getConnection()
    
    id = args.get('id')
    favorite_list = session.query(FavoriteList).filter(FavoriteList.id == id).first()
    if favorite_list is None:
        abort(404, message='no favorite list was found with this id')
    

    place_id = args.get('place_id')
    place = session.query(Place).filter(Place.id == place_id).first()
    if place is None:
        abort(404, message='no place was found with this id')

    if place not in favorite_list.places:
        abort(400, message="place is not in list")
        
    favorite_list.places.remove(place)
    session.commit()
    return {'message': 'place removed from list successfully'}
    
    '''
    place_id = args.get('place_id')
    for place in favorite_list.places:
        if place.id == place_id:
            favorite_list.places.remove(place)
            session.commit()
            return {'message': 'place removed from list successfully'}
    
    abort(400, message="place is not in list")
    '''