from sqlalchemy import Column, Integer, String, ForeignKey
from sqlalchemy.orm import relationship
from model.model import Base

class UserReview(Base):
    __tablename__ = 'user_reviews'

    id = Column(Integer, primary_key=True)
    user_id = Column(Integer, ForeignKey('users.id'))
    stars = Column(Integer)
    title = Column(String)
    content = Column(String)

    user = relationship('User', back_populates='user_reviews')