package boa.compiler.ast.statements;

import java.util.ArrayList;
import java.util.List;

import boa.compiler.ast.Component;
import boa.compiler.ast.Identifier;
import boa.compiler.visitors.AbstractVisitor;
import boa.compiler.visitors.AbstractVisitorNoArg;

/**
 * 
 * @author rdyer
 */
public class VisitStatement extends Statement {
	protected boolean before;
	protected boolean wildcard = false;
	protected Component node;
	protected final List<Identifier> ids = new ArrayList<Identifier>();
	protected Block body;

	public boolean isBefore() {
		return before;
	}

	public boolean hasWildcard() {
		return wildcard;
	}

	public boolean hasComponent() {
		return node != null;
	}

	public Component getComponent() {
		return node;
	}

	public List<Identifier> getIdList() {
		return ids;
	}

	public int getIdListSize() {
		return ids.size();
	}

	public Identifier getId(int index) {
		return ids.get(index);
	}

	public void addId(final Identifier id) {
		id.setParent(this);
		ids.add(id);
	}

	public Block getBody() {
		return body;
	}

	public void replaceBody(final Block body) {
		body.setParent(this);
		this.body = body;
	}

	public VisitStatement(final boolean before, final boolean wildcard, final Block body) {
		body.setParent(this);
		this.before = before;
		this.wildcard = wildcard;
		this.body = body;
	}

	public VisitStatement(final boolean before, final Component node, final Block body) {
		node.setParent(this);
		body.setParent(this);
		this.before = before;
		this.node = node;
		this.body = body;
	}

	/** {@inheritDoc} */
	@Override
	public <A> void accept(AbstractVisitor<A> v, A arg) {
		v.visit(this, arg);
	}

	/** {@inheritDoc} */
	@Override
	public void accept(AbstractVisitorNoArg v) {
		v.visit(this);
	}

	public VisitStatement clone() {
		final VisitStatement v = new VisitStatement(before, wildcard, body.clone());
		if (hasComponent())
			v.node = node.clone();
		for (final Identifier id : ids)
			v.addId(id.clone());
		copyFieldsTo(v);
		return v;
	}
}