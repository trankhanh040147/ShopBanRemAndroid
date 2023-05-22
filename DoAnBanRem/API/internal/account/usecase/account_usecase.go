package accountusecase

import (
	accountmodel "AndroidPadora/internal/account/model"
	"context"
	"errors"
)

type accountRepository interface {
	Create(context.Context, *accountmodel.AccountRegister) error
	FindDataWithCondition(context.Context, map[string]any) (*accountmodel.AccountLogin, error)
	//Update(context.Context, *accountmodel.UserUpdate, map[string]any) error
}

type accountUseCase struct {
	accountRepo accountRepository
}

func NewAccountUseCase(accountRepo accountRepository) *accountUseCase {
	return &accountUseCase{accountRepo}
}

func (u *accountUseCase) Register(ctx context.Context, data *accountmodel.AccountRegister) error {
	// Check email have been existed ?
	user, _ := u.accountRepo.FindDataWithCondition(ctx, map[string]any{"email": data.Email})
	if user != nil {
		return errors.New("Email da ton tai")
	}
	////Validate data
	//if err := data.Validate(); err != nil {
	//	return err
	//}

	// Prepare data before create user
	//// Must hash value of password before store in db
	//if err := data.PrepareCreate(); err != nil {
	//	return err
	//}

	if err := u.accountRepo.Create(ctx, data); err != nil {
		return err
	}

	return nil
}

func (u *accountUseCase) Login(ctx context.Context, data *accountmodel.AccountLogin) error {
	// Check email have been existed ?
	// B1: find user by email
	user, err := u.accountRepo.FindDataWithCondition(ctx, map[string]any{"email": data.Email})
	if err != nil {
		return err
	}

	if user.Password != data.Password {
		return errors.New("password is saiiiiiiiii")
	}
	// B2: Compare password of user with hashed password in db
	//if err := utils.Compare(user.Password, data.Password); err != nil {
	//	return nil, usermodel.ErrEmailOrPasswordInvalid
	//}

	// B3: Generate token
	//token, err := utils.GenerateJWT(utils.TokenPayload{user.Email, user.Role}, u.cfg)

	//if err != nil {
	//	return nil, common.ErrInternal(err)
	//}

	return nil
}

//func (u *userUseCase) UpdateProfile(ctx context.Context, data *usermodel.UserUpdate, userEmail string) error {
//	if err := u.userRepository.Update(ctx, data, map[string]any{"email": userEmail}); err != nil {
//		return common.ErrCannotUpdateEntity(usermodel.EntityName, err)
//	}
//	return nil
//}
