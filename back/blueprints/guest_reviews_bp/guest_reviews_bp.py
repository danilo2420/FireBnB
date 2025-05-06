from flask_smorest import Blueprint, abort

bp = Blueprint('guest_reviews_bp', __name__, url_prefix='/guest_reviews')

# R
@bp.route('/get_all')
def get_all_guest_reviews():
    ...


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