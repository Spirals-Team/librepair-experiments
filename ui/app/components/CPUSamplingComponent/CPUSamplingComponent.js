import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import { AutoSizer } from 'react-virtualized';
import fetchCPUSamplingAction from 'actions/AggregatedProfileDataActions';
import safeTraverse from 'utils/safeTraverse';
import Loader from 'components/LoaderComponent';
import MethodTree from 'components/MethodTreeComponent';
import Tabs from 'components/Tabs';
import styles from './CPUSamplingComponent.css';

export class CPUSamplingComponent extends Component {
  componentDidMount () {
    const {app, cluster, proc, workType, profileStart, selectedWorkType, profileDuration} = this.props.location.query;
    const { traceName } = this.props.params;
    this.props.fetchCPUSampling({
      app,
      cluster,
      proc,
      workType,
      selectedWorkType,
      traceName,
      query: {
        start: profileStart,
        duration: profileDuration
      },
    });
  }

  componentWillReceiveProps (nextProps) {
    const {app, cluster, proc, workType, profileStart, selectedWorkType, profileDuration} = nextProps.location.query;
    const didTraceNameChange = this.props.params.traceName !== nextProps.params.traceName;
    const didProfileChange = profileStart !== this.props.location.query.profileStart || profileDuration !== this.props.location.query.profileDuration;
    if (didTraceNameChange || didProfileChange) {
      const { traceName } = nextProps.params;
      this.props.fetchCPUSampling({
        app,
        cluster,
        proc,
        workType,
        selectedWorkType,
        traceName,
        query: {
          start: profileStart,
          duration: profileDuration
        },
      });
    }
  }

  render () {
    const { app, cluster, proc, fullScreen, profileStart, profileDuration } = this.props.location.query;
    const { traceName } = this.props.params;

    if (!this.props.tree.asyncStatus) return null;

    if (this.props.tree.asyncStatus === 'PENDING') {
      return (
        <div>
          <h4 style={{ textAlign: 'center' }}>Please wait, coming right up!</h4>
          <Loader />
        </div>
      );
    }

    if (this.props.tree.asyncStatus === 'ERROR') {
      return (
        <div className={styles.card}>
          <h2>Failed to fetch the data. Please refresh or try again later</h2>
        </div>
      );
    }

    return (
      <div>
        {!fullScreen && (
          <div style={{ position: 'relative' }}>
            <a
              href={`/work-type/cpu_sample_work/${traceName}?app=${app}&cluster=${cluster}&proc=${proc}&profileStart=${profileStart}&&profileDuration=${profileDuration}&workType=cpu_sample_work&fullScreen=true`}
              target="_blank"
              rel="noopener noreferrer"
              style={{ position: 'absolute', right: 10, top: 20, zIndex: 1 }}
            >
              <i
                className="material-icons"
                display
              >launch</i>
            </a>
          </div>
        )}
        {fullScreen && (
          <div className={styles.card} style={{ background: '#C5CAE9' }}>
            <div className="mdl-grid">
              <div className="mdl-cell mdl-cell--3-col">
                <div className={styles.label}>App</div>
                <strong className={styles.bold}>{app}</strong>
              </div>
              <div className="mdl-cell mdl-cell--3-col">
                <div className={styles.label}>Cluster</div>
                <strong className={styles.bold}>{cluster}</strong>
              </div>
              <div className="mdl-cell mdl-cell--3-col">
                <div className={styles.label}>Proc</div>
                <strong className={styles.bold}>{proc}</strong>
              </div>
              <div className="mdl-cell mdl-cell--3-col">
                <div className={styles.label}>Trace Name</div>
                <strong className={styles.bold}>{traceName} (CPU Sampling)</strong>
              </div>
            </div>
          </div>
        )}
        <Tabs>
          <div style={{display: "flex"}}>
            <div>Hot Methods</div>
            <div style={{display: "flex", flex: "1 1 auto"}}>
              <AutoSizer disableHeight>
                {({ width }) => (
                  <MethodTree
                    allNodes={safeTraverse(this.props, ['tree', 'data', 'allNodes'])}
                    nodeIndexes={safeTraverse(this.props, ['tree', 'data', 'terminalNodeIndexes'])}
                    nextNodesAccessorField="parent"
                    methodLookup={safeTraverse(this.props, ['tree', 'data', 'methodLookup'])}
                    filterKey="cs_hm_filter"
                    containerWidth={width}
                  />
                )}
              </AutoSizer>
            </div>
          </div>
          <div>
            <div>Call Tree</div>
            <div style={{display: "flex", flex: "1 1 auto"}}>
              <AutoSizer disableHeight>
                {({ width }) => (
                  <MethodTree
                    allNodes={safeTraverse(this.props, ['tree', 'data', 'allNodes'])}
                    nodeIndexes={safeTraverse(this.props, ['tree', 'data', 'treeRoot', 'children'])}
                    nextNodesAccessorField="children"
                    methodLookup={safeTraverse(this.props, ['tree', 'data', 'methodLookup'])}
                    filterKey="cs_ct_filter"
                    containerWidth={width}
                  />
                )}
              </AutoSizer>
            </div>
          </div>
        </Tabs>
      </div>
    );
  }
}

CPUSamplingComponent.propTypes = {
  fetchCPUSampling: PropTypes.func,
  params: PropTypes.shape({
    traceName: PropTypes.string.isRequired,
  }),
  tree: PropTypes.shape({
    asyncStatus: PropTypes.string,
    data: PropTypes.shape({
      allNodes: PropTypes.array,
      methodLookup: PropTypes.array,
      terminalNodeIndexes: PropTypes.array,
    }),
  }),
  location: PropTypes.shape({
    query: PropTypes.shape({
      app: PropTypes.string,
      cluster: PropTypes.string,
      proc: PropTypes.string,
      workType: PropTypes.string,
      profileStart: PropTypes.string,
      selectedWorkType: PropTypes.string,
      profileDuration: PropTypes.string
    }),
  }),
};

const mapStateToProps = state => ({
  tree: state.aggregatedProfileData || {},
});

const mapDispatchToProps = dispatch => ({
  fetchCPUSampling: params => dispatch(fetchCPUSamplingAction(params)),
});

export default connect(mapStateToProps, mapDispatchToProps)(CPUSamplingComponent);
