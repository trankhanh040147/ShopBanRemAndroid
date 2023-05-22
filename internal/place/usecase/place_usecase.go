package placeusecase

import (
	"context"
	"errors"
	placemodel "go01-airbnb/internal/place/model"
	"go01-airbnb/pkg/common"
)

type PlaceRepository interface {
	Create(context.Context, *placemodel.Place) error
	ListDataWithCondition(context.Context, *common.Paging, *placemodel.Filter, ...string) ([]placemodel.Place, error)
	FindDataWithCondition(context.Context, map[string]any, ...string) (*placemodel.Place, error)
	Update(context.Context, map[string]any, *placemodel.Place) error
	Delete(context.Context, map[string]any) error
}

type placeUseCase struct {
	placeRepo PlaceRepository
}

func NewPlaceUseCase(placeRepo PlaceRepository) *placeUseCase {
	return &placeUseCase{placeRepo}
}

func (uc *placeUseCase) CreatePlace(ctx context.Context, place *placemodel.Place) error {
	if err := place.Validate(); err != nil {
		return common.ErrBadRequest(err)
	}
	if err := uc.placeRepo.Create(ctx, place); err != nil {
		return common.ErrCannotCreateEntity(placemodel.EntityName, err)
	}
	return nil
}

func (uc *placeUseCase) GetPlaces(ctx context.Context, paging *common.Paging, filter *placemodel.Filter) ([]placemodel.Place, error) {

	// business logic
	//keys that are the models we want to link
	data, err := uc.placeRepo.ListDataWithCondition(ctx, paging, filter, "Owner", "Location")

	if err != nil {
		return nil, common.ErrCannotListEntity(placemodel.EntityName, err)
	}
	return data, nil
}

func (uc *placeUseCase) GetPlaceById(ctx context.Context, id int) (*placemodel.Place, error) {

	// business logic
	data, err := uc.placeRepo.FindDataWithCondition(ctx, map[string]any{"id": id}, "Owner", "Location")

	if err != nil {
		return nil, common.ErrEntityNotFound(placemodel.EntityName, err)
	}

	return data, nil
}

func (uc *placeUseCase) UpdatePlace(ctx context.Context, requester common.Requester, id int, place *placemodel.Place) error {
	if err := place.Validate(); err != nil {
		return common.ErrBadRequest(err)
	}
	currentData, err := uc.placeRepo.FindDataWithCondition(ctx, map[string]any{"id": id})
	if err != nil {
		return common.ErrEntityNotFound(placemodel.EntityName, err)
	}

	// check if requester user have permission to update this place ?
	if requester.GetUserRole() != "admin" && currentData.OwnerId != requester.GetUserId() {
		return common.ErrForbidden(errors.New("this user have no permission to update this place"))
	}
	if err := uc.placeRepo.Update(ctx, map[string]any{"id": id}, place); err != nil {
		return common.ErrCannotUpdateEntity(placemodel.EntityName, err)
	}
	return nil
}

func (uc *placeUseCase) DeletePlaceById(ctx context.Context, requester common.Requester, id int) error {
	currentData, err := uc.placeRepo.FindDataWithCondition(ctx, map[string]any{"id": id})
	if err != nil {
		return common.ErrEntityNotFound(placemodel.EntityName, err)
	}

	// check if requester user have permission to update this place ?
	if requester.GetUserRole() != "admin" && currentData.OwnerId != requester.GetUserId() {
		return common.ErrForbidden(errors.New("this user have no permission to update this place"))
	}
	if err := uc.placeRepo.Delete(ctx, map[string]any{"id": id}); err != nil {
		return common.ErrCannotDeleteEntity(placemodel.EntityName, err)
	}

	return nil
}
