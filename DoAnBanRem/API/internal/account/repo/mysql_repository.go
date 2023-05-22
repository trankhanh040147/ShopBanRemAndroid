package accountrepository

import (
	accountmodel "AndroidPadora/internal/account/model"
	"context"
	"errors"
	"gorm.io/gorm"
)

type userRepository struct {
	db *gorm.DB
}

// Constructor
func NewUserRepository(db *gorm.DB) *userRepository {
	return &userRepository{db: db}
}

func (r *userRepository) Create(ctx context.Context, data *accountmodel.AccountRegister) error {
	db := r.db.Begin()

	if err := db.Table(accountmodel.Account{}.TableName()).Create(data).Error; err != nil {
		db.Rollback()
		return err
	}

	if err := db.Commit().Error; err != nil {
		db.Rollback()
		return err
	}
	return nil
}

func (r *userRepository) FindDataWithCondition(ctx context.Context, condition map[string]any) (*accountmodel.AccountLogin, error) {
	var user accountmodel.AccountLogin

	if err := r.db.Table(accountmodel.Account{}.TableName()).Where(condition).First(&user).Error; err != nil {
		if err == gorm.ErrRecordNotFound {
			return nil, errors.New("record not found")
		}
		return nil, err
	}
	return &user, nil
}

//func (r *userRepository) Update(ctx context.Context, data *usermodel.UserUpdate, condition map[string]any) error {
//	if err := r.db.Table(usermodel.User{}.TableName()).Where(condition).Updates(data).Error; err != nil {
//		return common.ErrorDB(err)
//	}
//	return nil
//}
