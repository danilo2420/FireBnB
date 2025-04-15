from sqlalchemy import Column, Integer, Float, Enum
from model.model import Base
from model.placeCategoryEnum import PlaceCategory

class Place(Base):
    __tablename__ = 'places'

    id = Column(Integer, primary_key=True)
    type = Column(Enum(PlaceCategory))
    price_per_night = Column(Float)