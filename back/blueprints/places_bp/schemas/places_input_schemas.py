from marshmallow import Schema, fields

class Id_InputSchema(Schema):
    id = fields.Int(required=True)