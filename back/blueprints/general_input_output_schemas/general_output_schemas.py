from marshmallow import Schema, fields, validates_schema, ValidationError

class Success_OutputSchema(Schema):
    message = fields.Str()