"use strict";

angular.module('es.logongas.ix3').provider("validator", [function() {
        function ValidatorProvider() {
            this.mensajePatterns = {
                required: "No puede estar vacio.",
                email: "No tiene el formato de EMail",
                maxlength: "Debe tener un tamaño menor o igual a {{maxlength}}",
                minlength: "Debe tener un tamaño mayor o igual a {{minlength}}",
                pattern: "No cumple la expresión regular: '{{pattern}}'",
                min: "Debe ser un valor mayor o igual a {{min}}",
                max: "Debe ser un valor menor o igual a {{max}}",
                url: "No tiene el formato de una URL",
                integer: "El valor '{{value}}' no es un número"
            };
            this.getMensajePatterns = function() {
                return this.mensajePatterns;
            };
            this.$get = ['$interpolate', function($interpolate) {
                    return new Validator($interpolate, this.mensajePatterns);
                }];
        }

        function Validator($interpolate, mensajePatterns) {
            var that = this;
            this.mensajePatterns = mensajePatterns;

            function getNormalizeAttributeName(attributeName) {
                var normalizeAttributeName;
                var separator;

                if (attributeName.indexOf("-") >= 0) {
                    separator = "-";
                } else if (attributeName.indexOf(":") >= 0) {
                    separator = ":";
                } else {
                    separator = undefined;
                }

                var parts = attributeName.split(separator);
                normalizeAttributeName = parts[parts.length - 1];

                return normalizeAttributeName;
            }

            function getMessage(inputElement, errorType) {
                var realInputElement = inputElement[0];
                var messagePattern = that.mensajePatterns[errorType];
                if (typeof (messagePattern) === "undefined") {
                    messagePattern = errorType;
                }

                var messageEvaluator = $interpolate(messagePattern);

                var attributes = {
                    value: inputElement.val()
                };

                for (var attributeIndex in realInputElement.attributes) {
                    var attributeName = realInputElement.attributes[attributeIndex].nodeName;
                    if (attributeName) {
                        var value = realInputElement.attributes[attributeIndex].nodeValue;
                        var normalizeAttributeName = getNormalizeAttributeName(attributeName);
                        attributes[normalizeAttributeName] = value;
                    }
                }

                var message = messageEvaluator(attributes);
                return message;
            }

            /**
             * Dado el nombre de un "input" obtiene el label asociado
             * @param {element} inputElement Elemento del que se busca el label
             * @param {string} defaultLabel El label por defecto si no se encuentra ningún otro label
             */
            function getLabel(inputElement, defaultLabel) {
                var label;

                if (inputElement.attr('id')) {
                    var labelElement = $('label[for="' + inputElement.attr('id') + '"]');
                    if (labelElement.length > 0) {
                        label = $(labelElement[0]).text();
                    } else {
                        label = defaultLabel;
                    }
                } else {
                    label = defaultLabel;
                }

                return label;
            }

            /**
             * Genera la propiedad Label de una lista de mensajes
             * @param {BusinessMessages} businessMessages La lista ala que se añade la propiedad Label
             * @param {Formelement} formElement El formulario que contiene los elementos para poder obtener de él, los labels
             * @returns {BusinessMessages} La lista de entrada pero con la propieda label
             */
            function generateLabels(businessMessages, formElement) {
                if (angular.isArray(businessMessages)) {
                    for (var i in businessMessages) {
                        var businessMessage = businessMessages[i];
                        if (!businessMessage.label) {
                            var propertyName = businessMessage.propertyName;
                            var inputElement = $("[name='" + propertyName + "']", formElement);
                            var label = getLabel(inputElement, propertyName);
                            businessMessage.label = label;
                        }
                    }
                }
            }

            this.validateForm = function(angularForm, customValidations) {
                var businessMessages = [];

                var formElement = $("form[name='" + angularForm.$name + "']");

                for (var propertyName in angularForm) {
                    if (typeof (propertyName) === "string" && propertyName.charAt(0) !== "$") {
                        if (angularForm[propertyName].$error) {
                            for (var errorType in angularForm[propertyName].$error) {
                                if (angularForm[propertyName].$error[errorType] === true) {
                                    var inputElement = $("[name='" + propertyName + "']", formElement);
                                    businessMessages.push({
                                        propertyName: propertyName,
                                        message: getMessage(inputElement, errorType)
                                    });
                                }

                            }
                        }
                    }
                }

                //Ejecutamos las validaciones personalizadas pero solo si no hay mensajes "normales"
                //Esto se hace pq normalmente las validaciones personalizadas necesitan de los campos requeridos
                if (businessMessages.length === 0) {
                    if (angular.isArray(customValidations) === true) {
                        for (var i in customValidations) {
                            var customValidationFunction = customValidations[i];
                            var newBusinessMessage = customValidationFunction();

                            if (angular.isObject(newBusinessMessage)) {
                                businessMessages.push(newBusinessMessage);
                            }
                        }
                    } else if (angular.isFunction(customValidations) === true) {
                        var newBusinessMessage = customValidations();

                        if (angular.isObject(newBusinessMessage)) {
                            businessMessages.push(newBusinessMessage);
                        }
                    }
                }

                generateLabels(businessMessages, formElement);

                return businessMessages;
            };

        }

        return new ValidatorProvider();

    }]);

