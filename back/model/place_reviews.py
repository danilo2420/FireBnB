from sqlalchemy import Column, String, Integer, ForeignKey
from sqlalchemy.orm import relationship
from model.model import Base

class PlaceReview(Base):
    __tablename__ = 'place_reviews'

    id = Column(Integer, primary_key=True)
    hygiene = Column(Integer)
    post_accuracy = Column(Integer) # How accurate is the post for this particular place?
    service = Column(Integer)
    satisfaction = Column(Integer)
    stars = Column(Integer) # General grade taking everything into account

    place = relationship('Place', back_populates='reviews')