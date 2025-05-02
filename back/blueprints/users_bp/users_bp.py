from flask_smorest import Blueprint, arguments, response
from flask import jsonify
from blueprints.users_bp.schemas.users_input_schemas import *
from blueprints.users_bp.schemas.users_output_schemas import *
from connection import getConnection
from model.users import User

bp = Blueprint('users_bp', 'users_bp', url_prefix='/users')

@bp.route('/getAll')
@bp.response(200, GetAll_OutputSchema)
def get_all_users():
    session = getConnection()
    users = session.query(User)

    result = []
    for user in users:
        result.append(user.to_dict())

    result = {'users': result}

    return result

@bp.route('/get')
@bp.arguments(Get_InputSchema, location='query')
def get_user(args):
    return jsonify('working'), 200

@bp.route('/create', methods=['POST'])
def create_user():
    return jsonify('working'), 200

@bp.route('/update', methods=['PUT'])
def update_user():
    return jsonify('working'), 200

@bp.route('/delete')
def delete_user():
    return jsonify('working'), 200