(function (undefined) {
    "use strict";

    CambiarContrasenyaController.$inject = ['$scope', 'serviceFactory', 'formValidator'];
    function CambiarContrasenyaController($scope, serviceFactory, formValidator) {
        $scope.businessMessages = [];
        $scope.usuario=$scope.dialog.data;
        
        $scope.dialog.open({
            width: 550,
            height: 'auto',
            title: "Cambiar contraseña de " + $scope.usuario.name
        });



        $scope.buttonCancel = function () {
            $scope.dialog.closeCancel();
        };

        $scope.buttonOK = function () {
            var usuarioService = serviceFactory.getService("Usuario");

            $scope.businessMessages = formValidator.validate($scope.mainForm, $scope.$validators);
            if ($scope.businessMessages.length === 0) {
                usuarioService.updatePassword($scope.usuario.idIdentity, $scope.model.currentPassword, $scope.model.newPassword).then(function () {
                    alert("Su contraseña ha sido cambiada correctamente");
                    $scope.dialog.closeOK();
                }, function (businessMessages) {
                    $scope.businessMessages = businessMessages;
                });
            }
        };


        $scope.$validators = [
            {
                message: "La nueva contraseña y confirmar contraseña deben coincidir",
                rule: function () {
                    return ($scope.model.newPassword === $scope.model.confirmPassword)
                }
            }
        ]

    }

    angular.module("common").controller("CambiarContrasenyaController", CambiarContrasenyaController);

})();