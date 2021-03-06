"use strict";

angular.module('es.logongas.ix3.configuration').constant("ix3UserConfiguration", {
    bootstrap: {
        version: 3
    },
    server: {
        api: getContextPath() + "/api/empresa"
    },
    format: {
        date: {
            default: "dd/MM/yyyy"
        }
    },
    pages: {
        login: {
            absUrl: getContextPath() + "/site/index.html#/login"
        },
        forbidden: {
            url: "/forbidden"
        }
    },
    security: {
        defaultStatus: 401,
        acl: [
            ['user', 'state', 'params', function (user, state, params) {
                    if (user) {
                        if (user.tipoUsuario === "EMPRESA") {
                            return 200;
                        } else {
                            //Si no es una empresa prohibimos el acceso
                            return 403;
                        }
                    } else {
                        //Si no hay usuario, tiene que autenticarse
                        return 401;
                    }
                }]
        ]
    },
    session: {
        url: getContextPath() + "/api",        
        expand:"empresa,centro,titulado.direccion.municipio.provincia"
    },
    crud: {
        parentState:"lateralmenu",
        parentPathViews:"views"
    }
});