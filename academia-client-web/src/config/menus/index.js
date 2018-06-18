import React from 'react'

import Assessment from 'material-ui/svg-icons/action/assessment'
import Assignment from 'material-ui/svg-icons/action/assignment'
import DirectionsRun from 'material-ui/svg-icons/maps/directions-run'
import ErrorOutline from 'material-ui/svg-icons/alert/error-outline'
import GridOn from 'material-ui/svg-icons/image/grid-on'
import People from 'material-ui/svg-icons/social/people'
import Web from 'material-ui/svg-icons/av/web'

import {
  DASHBOARD,
  ACCOUNT_LIST,
  ACCOUNT_FORM,
  RESTRICTION_LIST,
  RESTRICTION_FORM,
  ACTIVITY_FORM,
  ACTIVITY_LIST,
  PLAN_FORM,
  PLAN_LIST,
} from '../routes'

import accountTypes from '../../constants/accountTypes'

export default [
  {
    key: DASHBOARD,
    link: DASHBOARD,
    text: 'Dashboard',
    icon: <Assessment />,
  },

  // account
  {
    key: ACCOUNT_LIST,
    text: 'Usuários',
    allowed: [accountTypes.ADMIN, accountTypes.TRAINER],
    icon: <People />,
    children: [
      {
        key: ACCOUNT_LIST,
        link: ACCOUNT_LIST,
        text: 'Listar Usuários',
        icon: <GridOn />,
      },
      {
        key: ACCOUNT_FORM,
        link: ACCOUNT_FORM,
        text: 'Criar Usuário',
        icon: <Web />,
      },
    ],
  },

  // restriction
  {
    key: RESTRICTION_LIST,
    text: 'Restrições',
    allowed: [accountTypes.ADMIN, accountTypes.TRAINER],
    icon: <ErrorOutline />,
    children: [
      {
        key: RESTRICTION_LIST,
        link: RESTRICTION_LIST,
        text: 'Listar Restrições',
        icon: <GridOn />,
      },
      {
        key: RESTRICTION_FORM,
        link: RESTRICTION_FORM,
        text: 'Criar Restrição',
        icon: <Web />,
      },
    ],
  },

  // activity
  {
    key: ACTIVITY_LIST,
    text: 'Atividades',
    allowed: [accountTypes.ADMIN, accountTypes.TRAINER],
    icon: <DirectionsRun />,
    children: [
      {
        key: ACTIVITY_LIST,
        link: ACTIVITY_LIST,
        text: 'Listar Atividades',
        icon: <GridOn />,
      },
      {
        key: ACTIVITY_FORM,
        link: ACTIVITY_FORM,
        text: 'Criar Atividade',
        icon: <Web />,
      },
    ],
  },

  // plan
  {
    key: PLAN_LIST,
    text: 'Planos',
    icon: <Assignment />,
    children: [
      {
        key: PLAN_LIST,
        link: PLAN_LIST,
        text: 'Listar Planos',
        icon: <GridOn />,
      },
      {
        key: PLAN_FORM,
        link: PLAN_FORM,
        text: 'Criar Plano',
        icon: <Web />,
      },
    ],
  },
]
