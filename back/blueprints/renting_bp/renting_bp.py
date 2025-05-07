from flask_smorest import Blueprint, abort
from blueprints.renting_bp.schemas.rentings_input_schemas import *
from blueprints.renting_bp.schemas.rentings_output_schemas import *
from connection import getConnection
from model.rentings import Renting
from model.users import User

bp = Blueprint('renting_bp', __name__, url_prefix='/rentings')

@bp.route('/hello')
def test():
    return 'world'

'''
Get
    All
    Get all user's rentings
    Get all place's rentings

    * Other good ones:
        Get past
        Get active
        Get to come

Createguest=10
Update
Delete
    Delete user's rentings
    Delete places's rentings
'''

# This one works a little differently than the rest; let's see what happens
@bp.route('/get')
@bp.arguments(Get_InputSchema, location='query')
@bp.response(200, Get_OutputSchema)
def get_rentings(args):
    result = []
    if len(args) == 0:
        result = getAllRentings()
    else:
        key = list(args.keys())[0]
        val = args.get(key)
        match key:
            case 'id':
                result.append(getRentingById(val))
            case 'guest':
                result.extend(getRentingsForGuest(val))
            case 'place':
                result.append(getRentingsForPlace(val))
            case _:
                print('something wrong about query input arguments')
    return {'rentings': result}

def getAllRentings():
    session = getConnection()
    rentings = session.query(Renting).all()
    return rentings

def getRentingById(id):
    session = getConnection()
    renting = session.query(Renting).filter(Renting.id == id).first()
    if renting is None:
        abort(404, message='no rental was found with this id')
    return renting

def getRentingsForGuest(guest_id):
    session = getConnection()

    guest = session.query(User).filter(User.id == guest_id).first()
    if guest is None:
        abort(404, message='no user was found with this id')
    
    return guest.rentings

def getRentingsForPlace(place_id):
    ...


@bp.route('/create', methods=['POST'])
def create_renting():
    ...


@bp.route('/update', methods=['PUT'])
def update_renting():
    ...


@bp.route('/delete', methods=['DELETE'])
def delete_renting():
    ...