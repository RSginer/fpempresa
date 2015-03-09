
app.controller('CreateAccountInitController', ['$scope', '$location', '$stateParams', 'repositoryFactory', 'formValidator', function ($scope, $location, $stateParams, repositoryFactory, formValidator) {
        var usuarioRepository = repositoryFactory.getRepository("Usuario");
        $scope.model = {};
        $scope.businessMessages = null;

        usuarioRepository.create().then(function (usuario) {
            $scope.model = usuario;
            if ($stateParams.tipoUsuario) {
                //Sobreescribimos el valor si nos los pasan en la URL
                $scope.model.tipoUsuario = $stateParams.tipoUsuario;
            }
        }, function (businessMessages) {
            $scope.businessMessages = businessMessages;
        });

        $scope.next = function () {

            $scope.businessMessages = formValidator.validate($scope.mainForm, $scope.$validators);
            if ($scope.businessMessages.length === 0) {
                $location.path("/createaccount/register/" + $scope.model.tipoUsuario);
            }

        };

        $scope.$validators = [
            {
                message: 'El registro de Centros Educativos no está habilitado',
                rule: function () {
                    if ($scope.model.tipoUsuario === "CENTRO") {
                        return false;
                    } else {
                        return true;
                    }
                }
            },
            {
                message: 'El registro de Empresas no está habilitado',
                rule: function () {
                    if ($scope.model.tipoUsuario === "EMPRESA") {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        ];


    }]);
