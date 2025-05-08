from flask_smorest import Blueprint, arguments, response, abort
from flask import jsonify
from blueprints.users_bp.schemas.users_input_schemas import *
from blueprints.users_bp.schemas.users_output_schemas import *
from blueprints.users_bp.schemas.schemas import *
from blueprints.users_bp.schemas.error_schemas import *
from blueprints.general_input_output_schemas.general_input_schemas import *
from blueprints.general_input_output_schemas.general_output_schemas import *
from connection import getConnection
from model.users import User

bp = Blueprint('users_bp', __name__, url_prefix='/users')

@bp.route('/get')
@bp.arguments(IdOptional_InputSchema, location='query')
@bp.response(200, GetAll_OutputSchema)
def get(args):
    id = args.get('id')
    
    if id is None:
        return getAllUsers()
    else:
        return getUser(id)

def getAllUsers():
    session = getConnection()
    users = session.query(User)

    result = []
    for user in users:
        result.append(user.to_dict())

    result = {'users': result}

    return result

def getUser(id):
    session = getConnection()
    user = session.query(User).filter(User.id == id).first()
    if user is None:
        abort(404, message='no user was found with this id')
    else:
        return {"users": [user]}

@bp.route('/create', methods=['POST'])
@bp.arguments(Create_InputSchema)
@bp.response(200, Success_OutputSchema)
def create_user(args):
    session = getConnection()

    user = User(
        name = args.get('name'),
        lastName = args.get('lastName'),
        age = args.get('age'),
        nationality = args.get('nationality'),
        description = args.get('description'),
        profile_image = args.get('profile_image'),
        stars = args.get('stars'),
        email = args.get('email')
    )

    session.add(user)
    session.commit()

    return {'message': 'success'}

@bp.route('/update', methods=['PUT'])
@bp.arguments(Update_InputSchema)
@bp.response(200, Success_OutputSchema)
def update_user(args):
    session = getConnection()
    id = args.get('id')

    user = session.query(User).filter(User.id == id).first()
    if user is None:
        abort(404)

    user.name = args.get('name', user.name)
    user.lastName = args.get('lastName', user.lastName)
    user.age = args.get('age', user.age)
    user.nationality = args.get('nationality', user.nationality)
    user.description = args.get('description', user.description)
    user.profile_image = args.get('profile_image', user.profile_image)
    user.stars = args.get('stars', user.stars)
    user.email = args.get('email', user.email)
    user.password = args.get('password', user.password)

    session.commit()

    return {'message': 'success'}

@bp.route('/delete', methods=['DELETE'])
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, Success_OutputSchema)
def delete_user(args):
    id = args.get('id')

    session = getConnection()

    user = session.query(User).filter(User.id == id).first()
    if user is not None:
        session.delete(user)
        session.commit()
        print('User deleted')
        return {'message': 'User was found and deleted successfully'}
    else:
        abort(404, message="No user with this id was found")