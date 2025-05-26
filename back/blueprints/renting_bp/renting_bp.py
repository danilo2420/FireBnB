from flask_smorest import Blueprint, abort
from blueprints.renting_bp.schemas.rentings_input_schemas import *
from blueprints.renting_bp.schemas.rentings_output_schemas import *
from blueprints.general_input_output_schemas.general_input_schemas import *
from blueprints.general_input_output_schemas.general_output_schemas import *
from connection import getConnection
from model.places import Place
from model.rentings import Renting
from model.users import User

bp = Blueprint('renting_bp', __name__, url_prefix='/rentings')

# This one works a little differently than the rest; let's see what happens
@bp.route('/get')
@bp.arguments(Get_InputSchema, location='query')
@bp.response(200, RentingGet_OutputSchema)
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
                result.extend(getRentingsForPlace(val))
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
    session = getConnection()

    place = session.query(Place).filter(Place.id == place_id).first()
    if place is None:
        abort(404, message='no place was found with this id')

    return place.rentings

@bp.route('/get_previews')
@bp.arguments(Id_InputSchema, location='query')
@bp.response(200, RentalPreviewList_OutputSchema)
def get_rental_previews(args):
    session = getConnection()

    guest_id = args.get('id')
    guest = session.query(User).filter(User.id == guest_id).first()
    if guest is None:
        abort(404, message="User does not exist")

    rentings = session.query(Renting).filter(Renting.guest_id == guest_id).all()
    previews = []
    for renting in rentings:
        preview = {
            "id": renting.id,
            "place_id": renting.place.id,
            "name": renting.place.name,
            "type": renting.place.type,
            "start_date": renting.start_date,
            "end_date": renting.end_date,
            "total_price": renting.total_price
        }
        previews.append(preview)
    
    return {"previews": previews}


@bp.route('/create', methods=['POST'])
@bp.arguments(RentingCreate_InputSchema)
@bp.response(200, Success_OutputSchema)
def create_renting(args):
    session = getConnection()
    # Check guest user exists
    guest_id = args.get('guest_id')
    guest = session.query(User).filter(User.id == guest_id).first()
    if guest is None:
        abort(404, message='guest user was not found with this id')

    # Check place exists
    place_id = args.get('place_id')
    place = session.query(Place).filter(Place.id == place_id).first()
    if place is None:
        abort(404, message='place was not found with this id')

    renting = Renting(
        guest_id = guest_id,
        place_id = place_id,
        start_date = args.get('start_date'),
        end_date = args.get('end_date'),
        total_price = args.get('total_price')
    )

    session.add(renting)
    session.commit()

    return {'message': 'success'}


@bp.route('/update', methods=['PUT'])
@bp.arguments(RentingUpdate_InputSchema)
@bp.response(200, Success_OutputSchema)
def update_renting(args):
    id = args.get('id')
    session = getConnection()

    renting = session.query(Renting).filter(Renting.id == id).first()
    if renting is None:
        abort(404, message='no renting was found with this id')
    
    renting.start_date = args.get('start_date', renting.start_date)
    renting.end_date = args.get('end_date', renting.end_date)
    renting.total_price = args.get('total_price', renting.total_price)
    session.commit()

    return {'message': 'renting updated successfully'}


@bp.route('/delete', methods=['DELETE'])
@bp.arguments(RentingDelete_InputSchema, location='query')
@bp.response(200, Success_OutputSchema)
def delete_renting(args):
    key = list(args.keys())[0]
    val = args.get(key)

    match key:
        case 'id':
            deleteRentingById(val)
        case 'guest':
            deleteRentingForGuest(val)
        case 'place':
            deleteRentingForPlace(val)
        case _:
            print('something wrong about query input arguments')
            return {'message': 'something wrong about query input arguments'}
        
    return {'message': 'rentings deleted successfully'}

def deleteRentingById(id):
    session = getConnection()

    renting = session.query(Renting).filter(Renting.id == id).first()
    if renting is None:
        abort(404, message='no renting was found with this id')
    
    session.delete(renting)
    session.commit()

def deleteRentingForGuest(guest_id):
    session = getConnection()

    guest = session.query(User).filter(User.id == guest_id).first()
    if guest is None:
        abort(404, message='no user was found with this id')

    for renting in guest.rentings:
        session.delete(renting)

    session.commit()

def deleteRentingForPlace(place_id):
    session = getConnection()

    place = session.query(Place).filter(Place.id == place_id).first()
    if place is None:
        abort(404, message='no place was found with this id')

    for renting in place.rentings:
        session.delete(renting)

    session.commit()